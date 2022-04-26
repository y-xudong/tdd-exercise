package args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {
    record BooleanOptions(@Option("l") boolean logging) {}
    record IntOptions(@Option("d") int port) {}
    record StringOptions(@Option("d") String directory) {}
    record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {}

    @Test
    void should_log_be_true_when_flag_l_presents() {
        BooleanOptions options = Args.parse(BooleanOptions.class, "-l");
        assertTrue(options.logging());
    }

    @Test
    void should_log_be_false_when_flag_l_does_not_present() {
        BooleanOptions options = Args.parse(BooleanOptions.class);
        assertFalse(options.logging());
    }

    @Test
    void should_port_be_8080_when_passing_p_as_8080() {
        IntOptions options = Args.parse(IntOptions.class, "8080");
        assertEquals(8080, options.port());
    }

    @Test
    void should_port_be_8081_when_passing_p_as_8081() {
        IntOptions options = Args.parse(IntOptions.class, "8081");
        assertEquals(8081, options.port());
    }

    @Test
    void should_directory_be_usr_local_when_set_as_it() {
        StringOptions options = Args.parse(StringOptions.class, "/usr/local");
        assertEquals("/usr/local", options.directory());
    }

    @Test
    void should_directory_be_usr_logs_when_set_as_it() {
        StringOptions options = Args.parse(StringOptions.class, "/usr/logs");
        assertEquals("/usr/logs", options.directory());
    }

//    TODO: 2. Multiple args:
//    TODO:   - e.g. -l -p 8080 -d /usr/logs

    @Test
    void should_parse_multiple_parameters() {
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }


//    TODO: 3. Error handling:
//    TODO:   - boolean: -l 123
//    TODO:   - int: -p hello
//    TODO:   - String: -d 123
//    TODO:   - multiple values after flag: -p hello 123
//    TODO: 4. Default values:
//    TODO:   - boolean: false
//    TODO:   - int: 0
//    TODO:   - String: ""
}
