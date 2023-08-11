package ru.practicum.shareit.common.handler;

import org.assertj.core.util.Lists;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.booking.exception.BookingIsAlreadyApprovedException;
import ru.practicum.shareit.common.GlobalExceptionHandler;
import ru.practicum.shareit.common.ModelNotFoundException;
import ru.practicum.shareit.item.exception.ItemUnavailableException;
import ru.practicum.shareit.item.exception.ItemWasNotBookedByUserException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleMethodArgumentNotValidException() {
        Method method = new Object() {
        }.getClass().getEnclosingMethod();
        MethodParameter parameter = Mockito.mock(MethodParameter.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(parameter.getMethod()).thenReturn(method);
        when(bindingResult.getAllErrors()).thenReturn(Lists.newArrayList());
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);
        assertNotNull(exceptionHandler.handleMethodArgumentNotValidException(ex));
    }

    @Test
    void handleModelNotFoundException() {
        ModelNotFoundException ex = new ModelNotFoundException("Msg");
        assertNotNull(exceptionHandler.handleModelNotFoundException(ex));
    }

    @Test
    void handleMissingRequestHeaderException() {
        MissingRequestHeaderException ex = mock(MissingRequestHeaderException.class);
        assertNotNull(exceptionHandler.handleMissingRequestHeaderException(ex));
    }

    @Test
    void handleDataIntegrityViolationException() {
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        ConstraintViolationException cvex = mock(ConstraintViolationException.class);
        when(ex.getCause()).thenReturn(cvex);
        assertNotNull(exceptionHandler.handleDataIntegrityViolationException(ex));
    }

    @Test
    void handleItemUnavailableException() {
        ItemUnavailableException ex = new ItemUnavailableException();
        assertNotNull(exceptionHandler.handleItemUnavailableException(ex));
    }

    @Test
    void handleBookingIsAlreadyApprovedException() {
        BookingIsAlreadyApprovedException ex = new BookingIsAlreadyApprovedException();
        assertNotNull(exceptionHandler.handleBookingIsAlreadyApprovedException(ex));
    }

    @Test
    void handleItemWasNotBookedByUserException() {
        ItemWasNotBookedByUserException ex = new ItemWasNotBookedByUserException();
        assertNotNull(exceptionHandler.handleItemWasNotBookedByUserException(ex));
    }

    @Test
    void handleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        assertNotNull(exceptionHandler.handleMethodArgumentTypeMismatchException(ex));
    }

    @Test
    void handleException() {
        Throwable ex = new Throwable();
        assertNotNull(exceptionHandler.handleException(ex));
    }
}