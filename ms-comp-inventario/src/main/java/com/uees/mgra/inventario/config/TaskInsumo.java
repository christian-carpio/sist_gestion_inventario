package com.uees.mgra.inventario.config;

import com.uees.mgra.inventario.modals.entity.Insumo;
import com.uees.mgra.inventario.repository.InsumoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
public class TaskInsumo {
    private final InsumoRepository insumoRepository;
    private static final Logger log = LoggerFactory.getLogger(TaskInsumo.class);

    public TaskInsumo(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    @Scheduled(cron = "${scheduled.task.cron}")
    public void updateInsumosToExpired() {
        log.info("inicio de la tarea");
        List<Insumo> productosCaducados = insumoRepository.findCaducado(LocalDate.now());
        productosCaducados.stream().parallel().forEach(insumo->{
            log.info(insumo.toString());
            insumo.setEsCaducado(true);
            insumoRepository.save(insumo);
        });
        log.info("fin de la tarea");
    }
}
