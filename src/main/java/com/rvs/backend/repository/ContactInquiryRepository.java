package com.rvs.backend.repository;

import com.rvs.backend.model.ContactInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactInquiryRepository extends JpaRepository<ContactInquiry, Long> {
    List<ContactInquiry> findAllByOrderByCreatedAtDesc();
}
