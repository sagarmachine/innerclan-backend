package com.innerclan.v1.service;

import com.innerclan.v1.entity.Design;

import java.util.List;

public interface IDesignService {
    List<Design> getAllDesigns();

    List<Design> getUnseenDesigns();

    List<Design> getSeenDesigns();

    void deleteDesign(long id);
}
