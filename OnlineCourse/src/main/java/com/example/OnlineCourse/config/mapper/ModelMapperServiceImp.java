package com.example.OnlineCourse.config.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelMapperServiceImp implements ModelMapperService{
    private ModelMapper modelMapper;
    @Override
    public ModelMapper forResponse() {
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE);  //RESPONSE LA REQUEST AYNI KOD VAR BURDA LOOSE KULLANILDI REQUESTTE STANDART KULLANILIYOR
        return this.modelMapper;                           //LOOSE YERİNE STRNCT TE DİYEBİLİRİZ AMA O YÜZ YÜZ UYUMLULUK İSTİYOR BU YÜZDEN GEREK YOK
    }

    @Override
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;


    }
}

