package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.InternalId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_ARGUMENT = "Argument contains special characters";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    //@@author fongwz
    /**
     * Parses {@code args} into a trimmed argument and returns it.
     * @throws IllegalValueException if the argument provided is invalid (contains special characters).
     */
    public static String parseArgument(String args) throws IllegalValueException {
        String parsedArgs = args.trim();
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(parsedArgs);
        boolean b = m.find();

        if (b) {
            throw new IllegalValueException(MESSAGE_INVALID_ARGUMENT);
        }
        return parsedArgs;
    }
    //@@author

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    //@@author Sri-vatsa
    /**
     * Parses a {@code Optional<String> location} into an {@code Optional<String>} if {@code location} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseLocation(Optional<String> location) throws IllegalValueException {
        requireNonNull(location);
        return location.isPresent() ? Optional.of(location.get()) : Optional.empty();
    }

    //@@author Sri-vatsa
    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<String>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(date.get()) : Optional.empty();
    }

    //@@author Sri-vatsa
    /**
     * Parses a {@code Optional<String> time} into an {@code Optional<String>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(time.get()) : Optional.empty();
    }

    //@@author Sri-vatsa
    /**
     * Parses {@code String date} & {@code String time} if {@code date} & {@code time} are present.
     */
    public static LocalDateTime parseDateTime(String date, String time) throws IllegalValueException {
        requireNonNull(date);
        requireNonNull(time);

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");
            String dateTime = date + " " + time;
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
            return localDateTime;
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException("Please enter a date & time in the format dd/mm/yyyy & hhmm respectively!");
        }
    }



    //@@author Sri-vatsa
    /**
     * Parses a {@code Optional<String> notes} into an {@code Optional<String>} if {@code notes} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseNotes(Optional<String> notes) throws IllegalValueException {
        requireNonNull(notes);
        return notes.isPresent() ? Optional.of(notes.get()) : Optional.empty();
    }

    //@@author Sri-vatsa
    /**
     * Parses {@code Collection<String> ids} into a {@code Set<>}.
     */
    public static ArrayList<InternalId> parseIds(Collection<String> ids) throws IllegalValueException {
        requireNonNull(ids);
        final ArrayList<InternalId> idSet = new ArrayList<>();
        for (String id : ids) {
            idSet.add(new InternalId(Integer.parseInt(id)));
        }
        return idSet;
    }
}
