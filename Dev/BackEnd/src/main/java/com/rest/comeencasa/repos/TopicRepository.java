package com.rest.comeencasa.repos;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface TopicRepository extends JpaRepository<Topic, Long> {


    List<Topic> findTopicByCategory(Category category);
    Boolean findTopicsByid(Long id);
    Topic findTopicByid(Long id);
}

