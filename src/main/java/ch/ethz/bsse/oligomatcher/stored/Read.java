/**
 * Copyright (c) 2011-2012 Armin Töpfer
 *
 * This file is part of OligoMatcher.
 *
 * OligoMatcher is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * OligoMatcher is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OligoMatcher. If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ethz.bsse.oligomatcher.stored;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Armin Töpfer (armin.toepfer [at] gmail.com)
 */
public class Read implements Serializable {

    private String read;
    private List<String> kmers;
    private String identifier;

    public Read(String read, String identifier, int K) {
        this.read = read;
        this.kmers = new ArrayList<>();
        this.identifier = identifier;
        this.split(K);
    }

    private void split(int K) {
        for (int i = K; i <= read.length(); i += 1) {
            this.kmers.add(this.read.substring(i - K, i));
        }
    }

    public List<String> getKmers() {
        return kmers;
    }

    public String getRead() {
        return read;
    }

    public String getIdentifier() {
        return identifier;
    }
}