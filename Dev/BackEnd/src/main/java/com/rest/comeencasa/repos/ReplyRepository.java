package com.rest.comeencasa.repos;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findRepliesByTopicId(Long topic_id);
}

