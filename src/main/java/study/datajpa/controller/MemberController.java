package study.datajpa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;
import study.datajpa.repository.MemberRepository;
import study.datajpa.repository.TeamRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        // 스프링이 컨버팅하는 과정을 다 해줘서 Member 객체를 바로 인젝션 해준다.
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<Member> list(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }

    @GetMapping("/members_page")
    public Page<Member> list2(@PageableDefault(size = 5, sort = "username") Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }

    @GetMapping("/members_dto")
    public Page<MemberDto> listDtos(Pageable pageable) {
        return memberRepository.findAll(pageable)
//                .map(m -> new MemberDto(m.getId(), m.getUsername(), null));
                .map(MemberDto::new);
    }

    @GetMapping("/members_teams")
    public String list(
            @Qualifier("member") Pageable memberPageable,
            @Qualifier("team") Pageable teamPageable
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Page<Member> memberPage = memberRepository.findAll(memberPageable);
        Page<Team> teamPage = teamRepository.findAll(teamPageable);

        return objectMapper.writeValueAsString(memberPage) + objectMapper.writeValueAsString(teamPage);
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i));
            teamRepository.save(new Team("team" + i));
        }
    }
}
