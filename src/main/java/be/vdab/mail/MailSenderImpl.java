package be.vdab.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import be.vdab.entities.Filiaal;

@Component
class MailSenderImpl implements MailSender {
	private final static Logger logger = Logger.getLogger(MailSenderImpl.class.getName());
	private final JavaMailSender sender;
	private final String webmaster;
	private final VelocityEngine velocityEngine;

	@Autowired
	MailSenderImpl(JavaMailSender sender, @Value("${webmaster}") String webmaster, VelocityEngine velocityEngine) {
		this.sender = sender;
		this.webmaster = webmaster;
		this.velocityEngine = velocityEngine;
	}

	@Async
	@Override
	public void nieuwFiliaalMail(final Filiaal filiaal, final String urlFiliaal) {
		try {
			/* without velocity
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(webmaster);
			helper.setSubject("Nieuw filiaal");
			//helper.setText(String.format("Filiaal <strong>%s</strong> is toegevoegd", filiaal.getNaam()), true);
			helper.setText(String.format(
					"Je kan het nieuwe filiaal <strong>%s</strong> " + "<a href='%s/wijzigen'>hier</a> nazien",
					filiaal.getNaam(), urlFiliaal), true);
			sender.send(message);
			*/
			
			// with velocity			
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					message.setTo("karim.romagnani@gmail.com");
					Map<String,Object> model = new HashMap<>();
					model.put("filiaal", filiaal.getNaam());
					model.put("urlFiliaal", urlFiliaal);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "velocity", "UTF-8", model);
					message.setText(text, true);
				}
			};
			this.sender.send(preparator);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "kan mail nieuw filiaal niet versturen", ex);
			throw new RuntimeException("Kan mail nieuw filiaal niet versturen", ex);
		}
	}

	@Override
	public void aantalFilialenMail(long aantal) {
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo(webmaster);
			helper.setSubject("Aantal filiaal");
			helper.setText(String.format("Er zijn <strong>%d</strong> filialen.", aantal), true);
			sender.send(message);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "kan mail aantal filialen niet versturen", ex);
			throw new RuntimeException("Kan mail niet versturen", ex);
		}

	}
}
