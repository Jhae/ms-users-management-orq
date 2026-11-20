package org.arones.mapper;

import org.arones.contract.ms.users.management.orq.model.UserManagementResponseDto;
import org.arones.contract.ms.users.model.UsuarioResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface UserManagementMapper {

    UserManagementResponseDto fromUsuarioRsToUserManagementRs(UsuarioResponseDto usuarioResponseDto);

}
