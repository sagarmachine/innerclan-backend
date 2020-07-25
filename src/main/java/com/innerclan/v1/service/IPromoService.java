package com.innerclan.v1.service;


import com.innerclan.v1.entity.Promo;

import java.util.HashMap;
import java.util.List;

public interface IPromoService {
    void addPromo(Promo promo);

    List<Promo> getPromosOrderByDate();

    void deletePromo(long id);

    HashMap<String,Double> isPromoValid(String promo, String email);

    List<Promo> getPublicPromos();
}
