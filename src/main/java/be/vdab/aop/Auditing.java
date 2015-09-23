package be.vdab.aop;

import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
class Auditing {
	private final static Logger logger = Logger.getLogger(Auditing.class.getName());

	// before advice point
	@Before("execution(* be.vdab.services.*.*(..))")
	void schrijfAuditBefore(JoinPoint joinPoint) {
		StringBuilder builder = new StringBuilder("\nTijdstip\t").append(new Date());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !"anonymousUser".equals(authentication.getName())) {
			builder.append("\nGebruiker\t").append(authentication.getName());
		}
		builder.append("\nMethod\t\t").append(joinPoint.getSignature().toLongString());
		for (Object object : joinPoint.getArgs()) {
			builder.append("\nParameter\t").append(object);
		}
		logger.info(builder.toString());
	}

	// after returning advice point (no exceptions)
	@AfterReturning(pointcut = "execution(* be.vdab.services.*.*(..))", returning = "returnValue")
	void schrijfAuditAfter(JoinPoint joinPoint, Object returnValue) {
		StringBuilder builder = new StringBuilder("\nTijdstip\t").append(new Date());
		if (returnValue != null) {
			builder.append("\nReturn\t\t");
			if (returnValue instanceof Collection) {
				builder.append(((Collection<?>) returnValue).size()).append(" objects");
			} else {
				builder.append(returnValue.toString());
			}
		}
		logger.info(builder.toString());
	}
}
