package study.jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.jpa.entity.Member;
import study.jpa.repository.MemberRepository;
import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable final Long id) {
        return this.memberRepository.findById(id).orElseThrow().getUsername();
    }

    @GetMapping(value = "/members2/{id}")
    public String findMember2(@PathVariable(value = "id") final Member member) {
        return member.getUsername();
    }

    @PostConstruct
    public void init() {
        this.memberRepository.save(new Member("userA"));
    }
}
