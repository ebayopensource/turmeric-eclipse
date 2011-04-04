/*******************************************************************************
 * Copyright (c) 2006-2010 eBay Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/
package org.ebayopensource.turmeric.eclipse.ui.views.registry;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import org.ebayopensource.turmeric.common.config.LibraryType;
import org.ebayopensource.turmeric.eclipse.core.resources.constants.SOATypeLibraryConstants;

/**
 * The Factory pattern gateway to expose the sorter. Right now the table has
 * only two columns and so the factory now handles only two columns. Later
 * people can extend it to add new sorters for additional columns. For the time
 * being if the column is not there in the factory the default viewer is
 * returned. 
 * 
 * @author smathew
 * 
 */
public class AbstractTypeSorterFactory {

	static TypeSorter typeSorter = new TypeSorter();
	static ViewerSorter viewerSorter = new ViewerSorter();
	static VersionSorter versionSorter = new VersionSorter();

	public static ViewerSorter createSorter(String columnName) {
		if (StringUtils.equals(columnName,
				SOATypeLibraryConstants.REGISTRY_VIEW_COLUMN_NAME)) {
			return typeSorter;
		} else if (StringUtils.equals(columnName,
				SOATypeLibraryConstants.REGISTRY_VIEW_COLUMN_VERSION)) {
			return versionSorter;
		}
		return viewerSorter;

	}

	/**
	 * Comparator that can compare Type name. Type object gets special treatment
	 * using the LibraryType.getName() . From the UI point of view this
	 * comparator is added to both the type column. Each column gets a special
	 * treatment.
	 * 
	 * @author smathew
	 * 
	 */
	static class TypeSorter extends ViewerSorter {
		/**
		 * Indicates the column; For faster performance we use boolean than an
		 * usual String.
		 */
		private int column;

		private TypeSorter() {
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof LibraryType && e2 instanceof LibraryType) {
				LibraryType type1 = (LibraryType) e1;
				LibraryType type2 = (LibraryType) e2;
				return type1.getName().compareTo(type2.getName());
			}
			return String.CASE_INSENSITIVE_ORDER.compare(e1.toString(), e2
					.toString());
		}

		public int getTypeColumn() {
			return column;
		}

		public void setTypeNameColumn(int column) {
			this.column = column;
		}
	}

	/**
	 * Comparator that can compare version. Type object gets special
	 * treatment using the LibraryType.getVersion() . From the UI point of view
	 * this comparator is added to the version column. Each column gets a
	 * special treatment.
	 * 
	 * @author smathew
	 * 
	 */
	static class VersionSorter extends ViewerSorter {
		/**
		 * Indicates the column; For faster performance we use boolean than an
		 * usual String.
		 */
		private int column;

		private VersionSorter() {
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof LibraryType && e2 instanceof LibraryType) {
				LibraryType type1 = (LibraryType) e1;
				LibraryType type2 = (LibraryType) e2;
				return type1.getVersion().compareTo(type2.getVersion());
			}
			return String.CASE_INSENSITIVE_ORDER.compare(e1.toString(), e2
					.toString());
		}

		public int getTypeColumn() {
			return column;
		}

		public void setTypeNameColumn(int column) {
			this.column = column;
		}
	}
}
