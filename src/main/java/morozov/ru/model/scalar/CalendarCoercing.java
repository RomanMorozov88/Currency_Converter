package morozov.ru.model.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class CalendarCoercing implements Coercing<Calendar, String> {

    private SimpleDateFormat simpleDateFormat;

    @Autowired
    CalendarCoercing(@Qualifier("time_bean") SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    @Override
    public String serialize(Object o) throws CoercingSerializeException {
        String result = null;
        if (o instanceof Calendar) {
            result = simpleDateFormat.format(((Calendar) o).getTime());
        }
        return result;
    }

    @Override
    public Calendar parseValue(Object o) throws CoercingParseValueException {
        Calendar result = null;
        if (o instanceof String) {
            try {
                result = Calendar.getInstance();
                result.setTime(simpleDateFormat.parse((String) o));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Calendar parseLiteral(Object o) throws CoercingParseLiteralException {
        Calendar result = null;
        if (o instanceof StringValue) {
            try {
                result = Calendar.getInstance();
                result.setTime(
                        simpleDateFormat.parse(((StringValue) o).getValue())
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}