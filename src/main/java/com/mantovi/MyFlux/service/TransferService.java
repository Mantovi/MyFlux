package com.mantovi.MyFlux.service;


import com.mantovi.MyFlux.dto.transfer.TransferRequestDTO;
import com.mantovi.MyFlux.dto.transfer.TransferResponseDTO;
import com.mantovi.MyFlux.model.User;

public interface TransferService {

    TransferResponseDTO createTransfer(TransferRequestDTO request, User user);
}
