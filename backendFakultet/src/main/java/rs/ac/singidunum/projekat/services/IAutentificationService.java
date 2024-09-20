package rs.ac.singidunum.projekat.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rs.ac.singidunum.projekat.dtos.RequestDTO;
import rs.ac.singidunum.projekat.dtos.ResponseDTO;


public interface IAutentificationService {
    ResponseDTO loginStudent(RequestDTO request);

    ResponseDTO loginProfesor(RequestDTO request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
