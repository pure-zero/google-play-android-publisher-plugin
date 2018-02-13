package org.jenkinsci.plugins.googleplayandroidpublisher;

import static hudson.Util.fixEmptyAndTrim;
import static org.jenkinsci.plugins.googleplayandroidpublisher.Util.REGEX_LANGUAGE;
import static org.jenkinsci.plugins.googleplayandroidpublisher.Util.REGEX_VARIABLE;
import static org.jenkinsci.plugins.googleplayandroidpublisher.Util.SUPPORTED_LANGUAGES;

import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.export.Exported;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.ComboBoxModel;
import hudson.util.FormValidation;

public final class RecentChanges extends AbstractDescribableImpl<RecentChanges> implements Serializable {

  private static final long serialVersionUID = 1;

  @Exported
  public final String language;

  @Exported
  public final String text;

  @DataBoundConstructor
  public RecentChanges(String language, String text) {
      this.language = language;
      this.text = text;
  }

  @Extension
  public static class DescriptorImpl extends Descriptor<RecentChanges> {

      @Override
      public String getDisplayName() {
          return "Recent changes";
      }

      public ComboBoxModel doFillLanguageItems() {
          return new ComboBoxModel(SUPPORTED_LANGUAGES);
      }

      public FormValidation doCheckLanguage(@QueryParameter String value) {
          value = fixEmptyAndTrim(value);
          if (value != null && !value.matches(REGEX_LANGUAGE) && !value.matches(REGEX_VARIABLE)) {
              return FormValidation.warning("Should be a language code like 'be' or 'en-GB'");
          }
          return FormValidation.ok();
      }

      public FormValidation doCheckText(@QueryParameter String value) {
          value = fixEmptyAndTrim(value);
          if (value != null && value.length() > 500) {
              return FormValidation.error("Recent changes text must be 500 characters or fewer");
          }
          return FormValidation.ok();
      }

  }

}