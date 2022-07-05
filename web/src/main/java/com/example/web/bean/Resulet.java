package com.example.web.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resulet {
    private Object data;
    private Integer code;
    private String message;

public static Resulet success(Object data){
    Resulet resulet=new Resulet();
    resulet.setCode(200);
    resulet.setMessage("success");
    resulet.setData(data);
    return resulet;
}

    public static Resulet fail(String message){
        Resulet resulet=new Resulet();
        resulet.setCode(404);
        resulet.setMessage(message);
        resulet.setData(null);
        return resulet;
    }



}
