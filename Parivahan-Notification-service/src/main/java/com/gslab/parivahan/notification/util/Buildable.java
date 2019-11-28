package com.gslab.parivahan.notification.util;


public interface Buildable<B> {
	B toBuilder();

	/**
	 * Builder used to create/build corresponding Model Entity
	 * 
	 * @param <T>
	 *            The Builder providing the creation of M
	 * @param <M>
	 *            The ouput IModelEntity type
	 * 
	 * @author vinodborole
	 */
	public interface Builder<T extends Builder<T, M>, M extends Buildable<?>> {

		/**
		 * Creates and return the Model Entity M
		 *
		 * @return M instance
		 */
		M build();

		/**
		 * Creates a Builder using the param M as the default values
		 *
		 * @param The
		 *            Model M
		 * @return Builder
		 */
		T from(M in);
	}
}
