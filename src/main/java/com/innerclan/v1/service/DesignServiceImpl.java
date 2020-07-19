package com.innerclan.v1.service;


import com.innerclan.v1.entity.Design;
import com.innerclan.v1.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignServiceImpl implements IDesignService {

    @Autowired
    DesignRepository designRepo;


    @Override
    public List<Design> getAllDesigns() {
        return designRepo.findAllByOrderByCreatedOnDesc();
    }

    @Override
    public List<Design> getUnseenDesigns() {
        return designRepo.findBySeenOrderByCreatedOnDesc(false);
    }

    @Override
    public List<Design> getSeenDesigns() {
        return designRepo.findBySeenOrderByCreatedOnDesc(true);
    }

}
