package be.vdab.aop;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class Performance {
	private final static Logger logger = Logger.getLogger(Performance.class.getName());

	@Around("be.vdab.aop.PointcutExpressions.services()")
	Object schrijfPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
		long voor = System.nanoTime();
		try {
			return joinPoint.proceed();
		} finally {
			long duurtijd = System.nanoTime() - voor;
			double seconden = (double) (duurtijd / 1000000000.00);
			logger.info(joinPoint.getSignature().toLongString() + " duurde " + seconden + " seconden");
		}
	}
}
