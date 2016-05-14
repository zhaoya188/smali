/*
 * Copyright 2016, Google Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.baksmali;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.jf.util.ConsoleUtil;
import org.jf.util.StringWrapper;

import javax.annotation.Nonnull;
import java.util.List;

@Parameters(commandDescription = "Shows usage information")
public class HelpCommand implements Command {
    @Nonnull private final JCommander jc;

    public HelpCommand(@Nonnull JCommander jc) {
        this.jc = jc;
    }

    @Parameter(description = "If specified, only show the usage information for the given commands")
    private List<String> commands;

    public void run() {
        if (commands == null || commands.isEmpty()) {
            jc.usage();
        } else {
            for (String cmd : commands) {
                if (cmd.equals("register-info")) {
                    String registerInfoHelp = "The --register-info parameter will cause baksmali to generate " +
                            "comments before and after every instruction containing register type " +
                            "information about some subset of registers. This parameter optionally accepts a " +
                            "comma-separated list of values specifying which registers and how much " +
                            "information to include. If no values are specified, \"ARGS,DEST\" is used as " +
                            "the default. Valid values include:\n" +
                            "    ALL: all pre- and post-instruction registers\n" +
                            "    ALLPRE: all pre-instruction registers\n" +
                            "    ALLPOST: all post-instruction registers\n" +
                            "    ARGS: any pre-instruction registers used as arguments to the instruction\n" +
                            "    DEST: the post-instruction register used as the output of the instruction\n" +
                            "    MERGE: any pre-instruction register that has been merged from multiple " +
                            "incoming code paths\n" +
                            "    FULLMERGE: an extended version of MERGE that also includes a list of all " +
                            "the register types from incoming code paths that were merged";

                    Iterable<String> lines = StringWrapper.wrapStringOnBreaks(registerInfoHelp,
                            ConsoleUtil.getConsoleWidth());
                    for (String line : lines) {
                        System.out.println(line);
                    }
                } else {
                    jc.usage(cmd);
                }
            }
        }
    }

    @Parameters(hidden =  true)
    public static class HlepCommand extends HelpCommand {
        public HlepCommand(@Nonnull JCommander jc) {
            super(jc);
        }
    }
}
