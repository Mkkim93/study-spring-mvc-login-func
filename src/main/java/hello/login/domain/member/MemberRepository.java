package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);           // Id 를 Map 내의 Long 객체에 저장한다
        log.info("save: member={}", member); // 로그를 출력한다
        store.put(member.getId(), member);   // put을 통해 Map 에 저장한다 Long : member.getId(), Member 객체에 memeber
        // member.getId() 가 저장된 member 이다.
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {

        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();

        /*List<Member> all = findAll();
        for (Member m : all) {
            if (m.getLoginId().equals(loginId)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();*/

    }

    public List<Member> findAll() {
        // store.values() : Map<> 에 있는 key 값 뺴고 value 값만 쭉 뽑는다. (List 타입으로 변환해서 보여준다)
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
