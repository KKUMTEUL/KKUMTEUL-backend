package com.kkumteul.domain.childprofile.repository;

import com.kkumteul.domain.childprofile.entity.ChildProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildProfileRepository extends JpaRepository<ChildProfile, Long> {
}
