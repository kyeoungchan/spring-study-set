package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"}) // team 은 넣지 않는 것 주의
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

/*
    protected Member() {
        // JPA 가 프록시 기술을 사용할 수 있게 하기 위해서 기본 생성자 필요
        // private 으로 하면 막힐 수 있으나, protected 로 설정하면 프록시 생성 가능
        // @NoArgsConstructor(access = AccessLevel.PROTECTED) 로 갈음
    }
*/

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
