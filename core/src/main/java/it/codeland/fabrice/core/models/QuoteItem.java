/*
 * Copyright 2015 Adobe Systems Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.codeland.fabrice.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuoteItem {

    @ValueMapValue(name = "quote")
    private String quote;

    @ValueMapValue(name = "author")
    private String author;

    @ValueMapValue(name = "year")
    private String year;

    public String getQuote() {
        return quote != null ? quote : "";
    }

    public String getAuthor() {
        return author != null ? author : "Unknown";
    }

    public String getYear() {
        return year;
    }
}
