package com.ourwork.service;



import com.ourwork.model.model;
import com.ourwork.store.store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class serviceTest {
    @Mock
    private store taskStore;

    @InjectMocks
    private service taskService = new service();

    private ArrayList<model> services;

    @BeforeEach
    void setUp() {
        services = new ArrayList<>();
    }

    @Test
    public void shouldSaveService() {
        when(taskStore.readModel()).thenReturn(services);

        model savedService = taskService.saveTask(new model(1L, "newservice"));

        assertNotNull(savedService.getuTime());
        verify(taskStore).writeTasks(any());
    }

    @Test
    public void shouldGetAllServices() {
        when(taskStore.readModel()).thenReturn(services);

        List<model > all = taskService.getAll();

        assertEquals(services, all);
    }

    @Test
    public void shouldFindService() {
        services.add(new model(1L, "task"));
        when(taskStore.readModel()).thenReturn(services);

        Optional<model> optionalservice = taskService.find(1L);

        model task = optionalservice.get();
        assertEquals(1L, task.getId());
        assertEquals("task", task.getIncluding());
    }

    @Test
    public void shouldGetEmptyService() {
        when(taskStore.readModel()).thenReturn(services);

        Optional<model> optionalservice = taskService.find(1L);

        assertFalse(optionalservice.isPresent());
    }

    @Test
    public void shouldUpdateService() {
        services.add(new model(1L, "task"));
        when(taskStore.readModel()).thenReturn(services);

        Optional<model> optionalservice = taskService.update(new model(1L, "new task"));

        model task = optionalservice.get();
        assertEquals(1L, task.getId());
        assertEquals("new task", task.getIncluding());
        assertNotNull(task.getuTime());
        verify(taskStore).writeTasks(any());
    }

    @Test
    public void shouldNotUpdateServiceWhenNotExist() {
        when(taskStore.readModel()).thenReturn(services);

        Optional<model> optionalservice = taskService.update(new model(1L, "new task"));

        assertFalse(optionalservice.isPresent());
        verify(taskStore, new Times(0)).writeTasks(any());
    }

    @Test
    public void shouldDeleteService() {
        services.add(new model(1L, "task"));
        when(taskStore.readModel()).thenReturn(services);

        Optional<model> optionalservice = taskService.delete(1L);

        model task = optionalservice.get();
        assertEquals(1L, task.getId());
        verify(taskStore).writeTasks(any());
    }

    @Test
    public void shouldNotDeleteServiceWhenNotExist() {
        when(taskStore.readModel()).thenReturn(services);

        Optional<model> optionalservice = taskService.delete(1L);

        assertFalse(optionalservice.isPresent());
        verify(taskStore, new Times(0)).writeTasks(any());
    }
}
