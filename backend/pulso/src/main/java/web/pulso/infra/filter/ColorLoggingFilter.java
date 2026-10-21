package web.pulso.infra.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ColorLoggingFilter extends OncePerRequestFilter {

    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        HttpServletRequest wrappedRequest =
                shouldWrap(request) ? new ContentCachingRequestWrapper(request) : request;

        long start = System.currentTimeMillis();
        filterChain.doFilter(wrappedRequest, response);
        long duration = System.currentTimeMillis() - start;

        String ip = extractIp(request);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();

        String timestamp = LocalDateTime.now().format(dateTimeFormatter);
        String statusText = getStatusText(status);

        String methodColor = getMethodColor(method);
        String statusColor = getStatusColor(status);

        String log = String.format(
                "%s [\u001B[90m%s\u001B[0m] : %s%-6s\u001B[0m %-40s %s%d %s\u001B[0m (%dms)",
                ip,
                timestamp,
                methodColor, method,
                uri,
                statusColor, status, statusText,
                duration
        );

        System.out.println(log);

        if (wrappedRequest instanceof ContentCachingRequestWrapper) {
            logRequestBody((ContentCachingRequestWrapper) wrappedRequest);
        }
    }

    private boolean shouldWrap(HttpServletRequest request) {
        String method = request.getMethod();
        return !method.equals("GET") && !method.equals("DELETE");
    }

    private String extractIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        return (forwarded != null && !forwarded.isBlank())
                ? forwarded
                : request.getRemoteAddr();
    }

    private void logRequestBody(ContentCachingRequestWrapper wrapper) {
        byte[] content = wrapper.getContentAsByteArray();
        if (content.length == 0) return;

        String body = new String(content, StandardCharsets.UTF_8);
        if (body.isBlank()) return;

        String prettyBody;
        try {
            prettyBody = new JSONObject(body).toString(2);
        } catch (Exception e) {
            prettyBody = body;
        }

        System.out.println("\u001B[90mRequest Body:\n" + prettyBody + "\u001B[0m");
    }

    private String getMethodColor(String method) {
        switch (method) {
            case "GET": return "\u001B[32m";
            case "POST": return "\u001B[34m";
            case "PUT": return "\u001B[33m";
            case "DELETE": return "\u001B[31m";
            default: return "\u001B[35m";
        }
    }

    private String getStatusColor(int status) {
        int group = status / 100;
        switch (group) {
            case 2: return "\u001B[32m";
            case 3: return "\u001B[36m";
            case 4:
            case 5: return "\u001B[31m";
            default: return "\u001B[0m";
        }
    }

    // 100% idêntico ao Kotlin
    private String getStatusText(int status) {
        switch (status) {
            case 100: return "Continue";
            case 101: return "Switching Protocols";
            case 102: return "Processing";
            case 200: return "OK";
            case 201: return "Created";
            case 202: return "Accepted";
            case 203: return "Non-Authoritative Information";
            case 204: return "No Content";
            case 205: return "Reset Content";
            case 206: return "Partial Content";
            case 300: return "Multiple Choices";
            case 301: return "Moved Permanently";
            case 302: return "Found";
            case 303: return "See Other";
            case 304: return "Not Modified";
            case 307: return "Temporary Redirect";
            case 308: return "Permanent Redirect";
            case 400: return "Bad Request";
            case 401: return "Unauthorized";
            case 402: return "Payment Required";
            case 403: return "Forbidden";
            case 404: return "Not Found";
            case 405: return "Method Not Allowed";
            case 406: return "Not Acceptable";
            case 407: return "Proxy Authentication Required";
            case 408: return "Request Timeout";
            case 409: return "Conflict";
            case 410: return "Gone";
            case 411: return "Length Required";
            case 412: return "Precondition Failed";
            case 413: return "Payload Too Large";
            case 414: return "URI Too Long";
            case 415: return "Unsupported Media Type";
            case 416: return "Range Not Satisfiable";
            case 417: return "Expectation Failed";
            case 418: return "I'm a teapot";
            case 422: return "Unprocessable Entity";
            case 425: return "Too Early";
            case 426: return "Upgrade Required";
            case 428: return "Precondition Required";
            case 429: return "Too Many Requests";
            case 431: return "Request Header Fields Too Large";
            case 451: return "Unavailable For Legal Reasons";
            case 500: return "Internal Server Error";
            case 501: return "Not Implemented";
            case 502: return "Bad Gateway";
            case 503: return "Service Unavailable";
            case 504: return "Gateway Timeout";
            case 505: return "HTTP Version Not Supported";
            default: return "Unknown";
        }
    }
}
