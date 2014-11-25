package edu.usc.infolab.roadnetwork;

import java.text.MessageFormat;

public class MBR {

	public MBR(double minLng, double minLat, double maxLng, double maxLat) {
		this.max = new Double[] { maxLng, maxLat };
		this.min = new Double[] { minLng, minLat };
	}

	public static MBR EMPTY() {
		return new MBR(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	}

	public static MBR ALL() {
		return new MBR(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	public void UnionWith(MBR mbr) {
		this.min[LAT_IDX] = Math.min(mbr.min[LAT_IDX], this.min[LAT_IDX]);
		this.max[LAT_IDX] = Math.max(mbr.max[LAT_IDX], this.max[LAT_IDX]);
		this.min[LNG_IDX] = Math.min(mbr.min[LNG_IDX], this.min[LNG_IDX]);
		this.max[LNG_IDX] = Math.max(mbr.max[LNG_IDX], this.max[LNG_IDX]);
	}

	public void Include(IGeoPoint p) {
		this.min[LAT_IDX] = Math.min(p.getLat(), this.min[LAT_IDX]);
		this.max[LAT_IDX] = Math.max(p.getLat(), this.max[LAT_IDX]);
		this.min[LNG_IDX] = Math.min(p.getLng(), this.min[LNG_IDX]);
		this.max[LNG_IDX] = Math.max(p.getLng(), this.max[LNG_IDX]);
	}

	boolean contain(MBR mbr) {
		// return ((m.minx >= minx) && (m.maxx <= maxx) && (m.miny >= miny) &&
		// (m.maxy <= maxy));
		boolean result = (this.min[LAT_IDX] <= mbr.min[LAT_IDX])
				&& (this.min[LNG_IDX] <= mbr.min[LNG_IDX])
				&& (this.max[LAT_IDX] >= mbr.max[LAT_IDX])
				&& (this.max[LNG_IDX] >= mbr.max[LNG_IDX]);
		return result;
	}

	public boolean contain(IGeoPoint p) {
		boolean inside = false;
		if (p.getLat() >= this.min[LAT_IDX] && p.getLat() <= this.max[LAT_IDX]
				&& p.getLng() >= this.min[LNG_IDX]
				&& p.getLng() <= this.max[LNG_IDX]) {
			inside = true;
		}
		return inside;
	}

	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof MBR) {
			MBR tmp = (MBR) obj;
			result = (tmp.MaxLat() == MaxLat() && tmp.MaxLng() == MaxLng()
					&& tmp.MinLat() == MinLat() && tmp.MinLng() == MinLng());
		}
		return result;
	}

	public int hashCode() {
		return MinLat().hashCode() ^ MinLng().hashCode() ^ MaxLat().hashCode()
				^ MaxLng().hashCode();
	}

	public String ToString() {
		return (new MessageFormat("min({0},{1}),max({2},{3})"))
				.format(new Object[] { this.min[LNG_IDX], this.min[LAT_IDX],
						this.max[LNG_IDX], this.max[LAT_IDX] });
	}

	// / Number of dimensions in a rectangle.
	public final int DIMENSIONS = 2;

	private final int LAT_IDX = 1;
	private final int LNG_IDX = 0;

	// array containing the minimum value for each dimension; ie { min(lng),
	// min(lat) }
	public Double[] max;

	// array containing the maximum value for each dimension; ie { max(lng),
	// max(lat) }
	public Double[] min;

	public Double MinLat() {
		return min[LAT_IDX];
	}

	public Double MaxLat() {
		return max[LAT_IDX];
	}

	public Double MinLng() {
		return min[LNG_IDX];
	}

	public Double MaxLng() {
		return max[LNG_IDX];
	}

	// / Return the height(maxLat-minLat)
	public Double Height() {

		return this.max[LAT_IDX] - this.min[LAT_IDX];
	}

	// / Return the height(maxLng-minLng)
	public double Width() {
		return this.max[LNG_IDX] - this.min[LNG_IDX];
	}

	public GeoPoint TopLeft() {
		return new GeoPoint(min[LAT_IDX], min[LNG_IDX]);
	}

	public GeoPoint BottomRight() {
		return new GeoPoint(max[LAT_IDX], max[LNG_IDX]);
	}
}