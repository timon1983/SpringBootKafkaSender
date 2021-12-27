package com.example.sbks.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicesS3ImplTest {

    @Mock
    ServicesS3Impl servicesS3;

    @Test
    void check_upload() {
        File file = new File("abc.txt");
        servicesS3.upload(file);
        verify(servicesS3).upload(file);
    }

    @Test
    void check_delete() {
        servicesS3.delete("abc.txt");
        verify(servicesS3).delete("abc.txt");
    }

    @Test
    void check_getFile__Should_Return_File() throws IOException {
        File file = new File("abc.txt");
        lenient().when(servicesS3.getFile("abc.txt")).thenReturn(file);
    }
}