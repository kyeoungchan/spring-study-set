package hello.advanced.trace;

import java.util.UUID;

public class TraceId {

    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
//        ab99e16f-3cde-4d24-8241-256108c203a2 //생성된 UUID
//        ab99e16f //앞 8자리만 사용
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public TraceId createNextId() {
        // 트랜잭션 id는 유지하면서, level 만 1 증가한다.
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() {
        // 트랜잭션 id는 유지하면서, level 만 1 감소한다.
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
