package home.hyywk.top.superwebsiterabbitmq.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 时间转化类
 */
@Configuration
public class DateConverterConfig implements Converter<String, Date> {
    private Logger logger = LoggerFactory.getLogger( DateConverterConfig.class );
    private static final List<String> formats = new ArrayList<>();

    static {
        formats.add("yyyy-MM-dd HH:mm:ss");
        formats.add("yyyy-MM-dd");
        formats.add("yyyy-MM-dd HH:mm");
        formats.add("yyyy-MM");
        formats.add("yyyy/MM/dd HH:mm:ss");
        formats.add("yyyy/MM/dd");
        formats.add("yyyy/MM/dd HH:mm");
        formats.add("yyyy/MM");
        formats.add("yyyyMMdd");
        formats.add("yyyyMM");
        formats.add("yyyy-MM-dd HH:mm:ss.SSS");
        formats.add("yyyy/MM/dd HH:mm:ss.SSS");
    }

    @Override
    public Date convert(String source) {
        String dateStr = source.trim();
        if (dateStr.isEmpty()) {
            return null;
        }
        if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(0));
        }
        if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(1));
        }
        if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(2));
        }
        if (dateStr.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(3));
        }
        if (dateStr.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(4));
        }
        if (dateStr.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(5));
        }
        if (dateStr.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(6));
        }
        if (dateStr.matches("^\\d{4}/\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(7));
        }
        if (dateStr.matches("^\\d{4}\\d{1,2}\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(8));
        }
        if (dateStr.matches("^\\d{4}\\d{1,2}$")) {
            return parseDate(dateStr, formats.get(9));
        }
        if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$")) {
            return parseDate(dateStr, formats.get(10));
        }
        if (dateStr.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$")) {
            return parseDate(dateStr, formats.get(4));
        }
        throw new IllegalArgumentException("Invalid date format '" + source + "'");
    }

    public Date parseDate(String dateStr, String format) {
        this.logger.info( "正在为其字段转换成时间", dateStr );
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
            return date;
        } catch (Exception e) {
            return date;
        }
    }
}