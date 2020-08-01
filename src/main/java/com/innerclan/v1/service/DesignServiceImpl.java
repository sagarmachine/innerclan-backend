package com.innerclan.v1.service;


import com.innerclan.v1.entity.Design;
import com.innerclan.v1.exception.DesignNotFoundException;
import com.innerclan.v1.repository.DesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void deleteDesign(long id) {
        Optional<Design> value= designRepo.findById(id);
                if(!value.isPresent()) throw new DesignNotFoundException("NO design found with id "+id);
                designRepo.deleteById(id);
    }

}
