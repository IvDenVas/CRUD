package com.example.crud.service;

import com.example.crud.dto.DepartmentDto;
/**
 * Интерфейс объявляет методы, реализующие бизнес-логику CRUD приложения.
 */
public interface DepartmentsService {

    /**
     * Метод получения департамента.
     *
     * @param id идентификатор департамента.
     * @return DepartmentDto dto департамента.
     * @throws RuntimeException при отсутствии в БД id.
     */
    DepartmentDto getDepartmentById(Long id);

    /**
     * Метод создания или обновления департамента.
     *
     * @param dto dto департамента
     * @return DepartmentDto dto департамента
     * @throws RuntimeException при отсутствии в БД id
     */
    DepartmentDto save(DepartmentDto dto);

    /**
     * Метод удаляет существующий департамент или выбрасывает ошибку.
     *
     * @param id идентификатор департамента
     * @throws RuntimeException при отсутствии в БД id
     */
    void deleteDepartmentByID(Long id);
}
