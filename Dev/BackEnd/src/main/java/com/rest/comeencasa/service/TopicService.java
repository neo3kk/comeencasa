package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Topic;
import com.rest.comeencasa.entities.TopicDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TopicService {

    List<Topic> findTopicsByCategory(String slug);

    List<TopicDTO> createListTopicDTO(List<Topic> list);

    TopicDTO makeTopicDTO(Topic topic);

    Topic findById(Long topicParam);

    boolean createTopic(Topic topic);

    Topic updateTopic(Topic topic);

    boolean deleteTopic(Long id, HttpServletRequest user);
}
