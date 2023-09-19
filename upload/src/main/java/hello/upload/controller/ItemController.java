package hello.upload.controller;

import hello.upload.domain.Item;
import hello.upload.domain.ItemRepository;
import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form) {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());
        /* 파일은 보통 데이터베이스에 저장하지 않고, 스토리지에 저장한다.
         * AWS 같은 거를 쓰면 S3 같은 데에 저장한다.
         * DB에는 파일을 저장하는 경로 같은 것만 저장하지, 파일 자체를 저장하지는 않는다.
         * 파일 경로도 Full Path도 보통 다 저장하지 않고, 기본 경로는 맞춰놓고, 그 이후에 상대적인 경로만 저장하는 편이다.*/

        // 데이터베이스에 저장
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);
        return "item-view";
    }

    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
//        "file:/Users/../uuid.확장자"
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        /* itemId를 파라미터로 적용한 이유
         * itemId에 접근할 수 있는 권한이나 사용자만 itemId를 받아서
         * 접근할 수 있는 권한이 있는 경우에만 해당 로직을 받도록 구현할 수 있다.
         * 현재 구현돼있지는 않음.*/
        Item item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName(); // 실제 사용자가 다운로드를 받을 때의 파일명을 사용하기 위함.

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); // 한글이나 특수문자 깨짐을 없애주기한 인코딩
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        /* 브라우저가 Content-Disposition 헤더를 보고 다운로드 받을지를 결정하게 된다.
         * 이 헤더를 설정하지 않으면 다운로드가 안 되고 바디에 파일 내용을 쓰기만 한다.*/
        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition) // 이거를 제외하면 다운로드가 되지 않는다.
                .body(resource);
    }
}
