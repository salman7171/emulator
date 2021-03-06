package com.manulaiko.tabitha.configuration;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.manulaiko.tabitha.exceptions.NotFound;
import com.manulaiko.tabitha.exceptions.filesystem.FileIsDirectory;
import com.manulaiko.tabitha.filesystem.File;

/**
 * Ini Configuration class
 *
 * This class is used to parse `*.ini` configuration files
 *
 * For accessing section members use a dot. Example:
 * Given the configuration file:
 *
 *     [core]
 *     verbose=true
 *
 *     [database]
 *     verbose=false
 *
 * The code to access the `verbose` members of each section would look like this:
 *
 *     try {
 *         IniConfiguration cfg = Configuration.loadIni("config.ini");
 *
 *         if(cfg.getBoolean("core.verbose")) {
 *             Console.println("true");
 *         } else {
 *             Console.println("false");
 *         }
 *
 *         if(cfg.getBoolean("database.verbose")) {
 *             Console.println("true");
 *         } else {
 *             Console.println("false");
 *         }
 *     } catch(Exception e) {
 *
 *     }
 *
 * This outputs the following:
 *
 *     true
 *     false
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.tabitha.configuration
 *
 * @see http://stackoverflow.com/a/15638381
 */
public class IniConfiguration implements IConfiguration
{
    /**
     * Path to the configuration file
     */
    public String path = "";

    /**
     * File handler
     */
    private File _handler;

    /**
     * Regexp pattern used to identify sections
     */
    private Pattern _section = Pattern.compile("\\s*\\[([^]]*)\\]\\s*");

    /**
     * Patter used to identify members
     */
    private Pattern _keyValue = Pattern.compile("\\s*([^=]*)=(.*)");

    /**
     * Entries from the configuration file
     */
    private Map<String, Map<String, String>> _entries = new HashMap<String, Map<String, String>>();

    /**
     * Parses the configuration file
     *
     * @param path Path to the configuration file
     *
     * @throws NotFound        If configuration file does not exists
     * @throws FileIsDirectory If `path` is a directory
     * @throws IOException     If couldn't read configuration file
     */
    public void parse(String path) throws NotFound, FileIsDirectory, IOException
    {
        this._handler = new File(path);
        this.path = this._handler.path;

        String section = null;
        for(String line : this._handler.getAllLines()) {
            Matcher m = _section.matcher(line);

            if(m.matches()) {
                section = m.group(1).trim();
            } else if(section != null) {
                m = _keyValue.matcher(line);

                if(m.matches()) {
                    String key   = m.group(1).trim();
                    String value = m.group(2).trim();

                    Map<String, String > kv = _entries.get(section);

                    if(kv == null) {
                        _entries.put(section, kv = new HashMap<String, String>());
                    }

                    kv.put(key, value);
                }
            }
        }
    }

    /**
     * Returns given configuration parameter as a short
     *
     * @param name Configuration parameter
     *
     * @return Given configuration parameter value
     */
    public short getShort(String name)
    {
        return Short.parseShort(this.getString(name));
    }

    /**
     * Returns given configuration parameter as an int
     *
     * @param name Configuration parameter
     *
     * @return Given configuration parameter value
     */
    public int getInt(String name)
    {
        return Integer.parseInt(this.getString(name));
    }

    /**
     * Returns given configuration parameter as a long
     *
     * @param name Configuration parameter
     *
     * @return Given configuration parameter value
     */
    public long getLong(String name)
    {
        return Long.parseLong(this.getString(name));
    }

    /**
     * Returns given configuration parameter as a string
     *
     * @param name Configuration parameter
     *
     * @return Given configuration parameter value
     */
    public String getString(String name)
    {
        String[] n       = name.split("\\.");
        String   section = "";
        String   member  = "";
        String   ret     = "";


        if(n.length <= 0) {
            return ret;
        } else if(n.length > 2) {
            section = n[0];

            for(int i = 1; i < n.length; i++) {
                member += n[i];

                if(i != n.length) {
                    member += ".";
                }
            }
        } else {
            section = n[0];
            member  = n[1];
        }

        Map<String, String> sec = this._entries.get(section);
        if(sec == null) {
            return ret;
        }

        if(sec.get(member) != null) {
            ret = sec.get(member);
        }

        return ret;
    }

    /**
     * Returns given configuration parameter as a boolean
     *
     * @param name Configuration parameter
     *
     * @return Given configuration parameter value
     */
    public boolean getBoolean(String name)
    {
        return Boolean.getBoolean(this.getString(name));
    }

    /**
     * Returns given configuration parameter as a float
     *
     * @param name Configuration parameter
     *
     * @return Given configuration parameter value
     */
    public float getFloat(String name)
    {
        return Float.parseFloat(this.getString(name));
    }

    /**
     * Returns given configuration parameter as a double
     *
     * @param name Configuration parameter
     *
     * @return Given configuration parameter value
     */
    public double getDouble(String name)
    {
        return Double.parseDouble(this.getString(name));
    }

    /**
     * Returns given configuration parameter as a byte
     *
     * @param name Configuration parameter
     *
     * @return Given configuration parameter value
     */
    public byte getByte(String name)
    {
        return Byte.parseByte(this.getString(name));
    }
}
