package com.innerclan.v1.service;

import com.innerclan.v1.entity.Access;
import com.innerclan.v1.entity.Client;
import com.innerclan.v1.entity.Promo;
import com.innerclan.v1.exception.ClientNotFoundException;
import com.innerclan.v1.exception.PromoAlreadyExistException;
import com.innerclan.v1.exception.PromoNotFoundException;
import com.innerclan.v1.repository.ClientRepository;
import com.innerclan.v1.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
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
           promo.setName(promo.getName().toUpperCase());
        try {
            Calendar calendar= Calendar.getInstance();
            calendar.add(Calendar.MONTH,3);
            promo.setExpiryDate(calendar.getTime());
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
    public HashMap<String,Double> isPromoValid(String promo, String email) {

        Optional<Client> value= clientRepository.findByEmail(email);
        if(!value.isPresent()) throw new ClientNotFoundException("Client with email :"+email+" not found");
          Client client= value.get();
        HashMap<String,Double> result = new HashMap<>();
        List<Promo> allPromos = promoRepository.findAll();
        for(Promo p:allPromos){

            if(p.getName().equalsIgnoreCase(promo)){
               if(client.getPromos().contains(p))
                    result.put("Promo Code Already Used",p.getValue());
               else if(p.getExpiryDate().compareTo(new java.util.Date())<0)
                    result.put("Promo Code Expired",p.getValue());
               else
                    result.put("Valid Promo Code",p.getValue());

               return result;
            }

        }

        result.put("INVALID PROMO CODE",-1.0);
        return result;


    }

    @Override
    public List<Promo> getPublicPromos() {

        Access access = Access.PUBLIC;
         List<Promo> result= promoRepository.findByAccess(access);
         setUsedBy(result);
        return result;
    }

    private void setUsedBy(List<Promo> promoList){
        for(Promo p:promoList){
            p.setUsedBy(p.getClients().size());
        }
    }

}
