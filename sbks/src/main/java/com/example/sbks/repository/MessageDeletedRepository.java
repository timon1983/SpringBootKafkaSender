package com.example.sbks.repository;

import com.example.sbks.model.MessageDeleted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDeletedRepository extends JpaRepository<MessageDeleted, Long> {
}
