package dev.pedrocarrara.sysAcademy.service;

import dev.pedrocarrara.sysAcademy.repository.PlanoRepository;
import org.springframework.stereotype.Service;

@Service
public final class PlanoService {

    private final PlanoRepository planoRepository;

    public PlanoService(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }


}
