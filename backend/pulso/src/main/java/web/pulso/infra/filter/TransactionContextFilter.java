package web.pulso.infra.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import web.pulso.utils.TransactionContext;

import java.io.IOException;

@Component
public class TransactionContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {

            TransactionContext.getTransactionId();

            filterChain.doFilter(request, response);

        } finally {

            TransactionContext.clear();
        }
    }
}