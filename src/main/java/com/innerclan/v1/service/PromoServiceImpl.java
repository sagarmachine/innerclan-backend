package com.innerclan.v1.service;

import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Promo;
import com.innerclan.v1.exception.InvalidClientPromoException;
import com.innerclan.v1.exception.PromoAlreadyExistException;
import com.innerclan.v1.exception.PromoNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.ProductRepository;
import com.innerclan.v1.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@Service
public class PromoServiceImpl implements IPromoService {


    @Autowired
    PromoRepository promoRepository;

    @Autowired
    ClientRepository clientRepository;


    @Override
    public void addPromo(Promo promo) {

        try {
            promoRepository.save(promo);
        } catch (Exception ex) {
            throw new PromoAlreadyExistException("Promo name " + promo.getName() + " already exist ");
        }


    }

    @Override
    public List<Promo> getPromosOrderByDate() {
       return promoRepository.findAllByOrderByCreatedOnDesc();
    }

    @Override
    public void deletePromo(long id) {
        Optional <Promo> value =promoRepository.findById(id);
        if(!value.isPresent()) throw new PromoNotFoundException("No Promo with id "+id+" found.");

        promoRepository.deleteById(id);

    }

    @Override
    public boolean isPromoValid(String promo, String email) {

        Optional<Client> value= clientRepository.findByEmail(email);
        if(!value.isPresent()) throw new InvalidClientPromoException("Cleint with id :"+email+" doesnot exist");

        List<Promo> allPromos = promoRepository.findAll();
        for(Promo p:allPromos){
            if(p.equals(promo)){
                

            }

        }

        return false;
    }
}
