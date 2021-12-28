package com.example.sbks.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RepositoryS3ImplTest {

    @Mock
    RepositoryS3Impl repositoryS3;

    @Test
    void save() {
        repositoryS3.save(Path.of("abc.txt").toFile(), "abc.txt");
        verify(repositoryS3).save(Path.of("abc.txt").toFile(), "abc.txt");
    }

    @Test
    void delete() {
        repositoryS3.delete("abc.txt");
        verify(repositoryS3).delete("abc.txt");
    }

    @Test
    void download() throws IOException {
        lenient().when(repositoryS3.download("abc.txt")).thenReturn(Path.of("abc.txt").toFile());
    }
}