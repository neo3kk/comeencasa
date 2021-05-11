package com.rest.comeencasa.service;

import com.rest.comeencasa.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    TopicService topicService;

    @Autowired
    UserService userService;

    @Override
    public Reply createReply(Long id, String content) {
        Reply reply = new Reply();
        reply.setContent(content);
        Topic topic = topicService.findById(id);
        reply.setTopic(topic);
        reply.setCreated_at(utils.getToday());
        reply.setUpdated_at(utils.getToday());
        return reply;
    }

    @Override
    public Reply save(Reply reply) {
        return replyRepository.save(reply);

    }

    @Override
    public List<ReplyDTO> createListReplyDTO(List<Reply> list) {
        List<ReplyDTO> listDTO = new ArrayList<>();
        list.forEach(reply -> {
            ReplyDTO replyDTO = makeReplyDTO(reply);
            listDTO.add(replyDTO);
        });
        return listDTO;
    }

    @Override
    public ReplyDTO makeReplyDTO(Reply reply) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setContent(reply.getContent());
        replyDTO.setCreatedAt(reply.getCreated_at());
        replyDTO.setUser(userService.makeUserDTO(reply.getUser()));
        replyDTO.setUpdatedAt(reply.getUpdated_at());
        replyDTO.set_id(reply.getId());
        replyDTO.setId(reply.getId());
        replyDTO.setTopic(reply.getTopic().getId());
        return replyDTO;
    }

    @Override
    public Reply updateReply(Long id, String content) {
        Reply reply = replyRepository.findById(id).get();
        reply.setContent(content);
        reply.setUpdated_at(utils.getToday());
        Reply replyUpdated = replyRepository.save(reply);
        return replyUpdated;

    }

    @Override
    public boolean deleteReply(Long id, HttpServletRequest request) {
        User user = userService.getUerRequest(request);
        Reply reply = replyRepository.findById(id).get();
        if(user.getId().equals(reply.getUser().getId())){
        try{
            replyRepository.delete(reply);
            return true;
        }catch (Exception e){
            return false;
        }
        }
        return false;
    }
}
