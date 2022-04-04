package study.jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.jpa.dto.MemberDto;
import study.jpa.entity.Member;
import study.jpa.repository.MemberRepository;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping(value = "/members/{id}")
    public String findMember(@PathVariable final Long id) {
        return this.memberRepository.findById(id).orElseThrow().getUsername();
    }

    @GetMapping(value = "/members2/{id}")
    public String findMember2(@PathVariable(value = "id") final Member member) {
        return member.getUsername();
    }

    @GetMapping(value = "members")
    public Page<MemberDto> list(@PageableDefault(size = 5, sort = "username") final Pageable pageable) {
        return this.memberRepository.findAll(pageable).map(MemberDto::new);
    }

    //    @PostConstruct
    public void init() {
        IntStream.range(0, 100).mapToObj(i -> new Member("user" + i, i)).forEach(this.memberRepository::save);
    }
}
