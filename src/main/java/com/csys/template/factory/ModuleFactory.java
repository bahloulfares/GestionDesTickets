package com.csys.template.factory;

import com.csys.template.domain.Module;
import com.csys.template.dto.ModuleDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModuleFactory {
  public static ModuleDTO moduleToModuleDTO(Module module) {
    ModuleDTO moduleDTO=new ModuleDTO();
    moduleDTO.setIdModule(module.getIdModule());
    moduleDTO.setDesignation(module.getDesignation());
    moduleDTO.setActif(module.getActif());
    return moduleDTO;
  }

  public static Module moduleDTOToModule(ModuleDTO moduleDTO) {
    Module module=new Module();
    module.setIdModule(moduleDTO.getIdModule());
    module.setDesignation(moduleDTO.getDesignation());
    module.setActif(moduleDTO.getActif());
    return module;
  }

  public static Collection<ModuleDTO> moduleToModuleDTOs(Collection<Module> modules) {
    List<ModuleDTO> modulesDTO=new ArrayList<>();
    modules.forEach(x -> {
      modulesDTO.add(moduleToModuleDTO(x));
    } );
    return modulesDTO;
  }
}

