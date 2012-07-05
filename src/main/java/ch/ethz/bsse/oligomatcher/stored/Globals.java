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

import java.util.concurrent.ForkJoinPool;

/**
 * @author Armin Töpfer (armin.toepfer [at] gmail.com)
 */
public class Globals {

    public static final ForkJoinPool fjPool = new ForkJoinPool();

}
