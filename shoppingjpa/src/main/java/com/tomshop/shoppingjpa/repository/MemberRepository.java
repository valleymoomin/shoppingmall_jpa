package com.tomshop.shoppingjpa.repository;

import com.tomshop.shoppingjpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

}
