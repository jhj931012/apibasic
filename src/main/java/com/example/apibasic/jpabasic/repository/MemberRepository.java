package com.example.apibasic.jpabasic.repository;

import com.example.apibasic.jpabasic.entity.Gender;
import com.example.apibasic.jpabasic.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Jpa로 CRUD Operation을 하려면 JpaRepository 인터페이스를 상속
//제네릭 타입으로 첫번째로 CRUD할 엔터티 클래스타입,
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 쿼리 메서드
    List<MemberEntity> findByGender(Gender gender);

    List<MemberEntity> findByAccountAndGender(String account, Gender gender);

    List<MemberEntity> findByNickNameContaining(String nickName);

    // JPQL 사용
    // select 별칭 from 엔터티클래스명 as 별칭 where 별칭.필드명
    // native-sql : select m.user_code from tbl_member as m
    // jpql : select m.userId from MemberEntity as m
    // 계정명으로 회원 조회
    @Query("select m from MemberEntity as m where m.account=?1")
    MemberEntity getMemberByAccount(@Param("acc") String acc);

    // 닉네임과 성별 동시만족 조건으로 회원 조회
    @Query("select m from MemberEntity as m where m.nickName=:nick and m.gender=:gen")
    List<MemberEntity> getMembersByNickAndGender(String nick, Gender gen);

    @Query("select m from MemberEntity m where m.nickName like %:nick%")
    List<MemberEntity> getMembersByNickName(String nick);

    @Modifying // 수정 삭제할 때 붙이기
    @Query("delete from MemberEntity m where m.nickName=:nick")
    void deleteByNickName(String nick);
}
