package morozov.ru.model.scalar;

import graphql.schema.GraphQLScalarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Что бы GraphQL мог работать с java.util.Calendar
 */
@Component
public class GraphQLCalendar extends GraphQLScalarType {

    @Autowired
    public GraphQLCalendar(CalendarCoercing calendarCoercing) {
        super("Calendar", "Calendar type", calendarCoercing);
    }
}