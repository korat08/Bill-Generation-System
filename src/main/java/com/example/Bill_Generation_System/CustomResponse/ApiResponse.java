package com.example.Bill_Generation_System.CustomResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private Boolean isSuccess;
    private String message;
    private T data;

}
