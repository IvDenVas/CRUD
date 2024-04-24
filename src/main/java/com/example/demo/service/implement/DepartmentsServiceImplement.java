package com.example.demo.service.implement;

import com.example.demo.repositorys.DepartmentsRepo;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentsServiceImplement implements DepartmentsService {

    private DepartmentsRepo repo;
    @Override
    public Department getDepartmentById(Long id) {
        return  repo.findById(id).get();
    }

//    @Override
//    public void save(Department department) {
//        if (department.getId() == null) {
//            dao.save(department);
//        } else {
//            dao.updateDepartmentById(department.getAddress(), department.getId());
//        }
//    }

    @Override
    public Department save(Department department) {
        return repo.save(department);
    }

//    public Department save2(Department department) {
//        if (department.getId() == null) {
//            dao.save(department);
//        } else {
//            // Проверка существования записи с переданным id
//            if (!departmentService.existsById(department.getId())) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            department.setModificationDate(new Date());
//        }
//
//        Department savedDepartment = departmentService.saveOrUpdateDepartment(department);
//        return new ResponseEntity<>(savedDepartment, HttpStatus.OK);
//        return dao.save(department);
//    }


    @Override
    public void deleteDepartmentByID(Long id) {
        repo.deleteById(id);
    }

//    @Override
//    public Long getMaxId() {
//        return dao.getMaxID().get().getId();
//    }
}
